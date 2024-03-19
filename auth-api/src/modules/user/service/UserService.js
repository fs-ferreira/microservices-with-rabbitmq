import * as httpStatus from "../../../config/constants/httpStatus.js"
import { API_SECRET } from "../../../config/constants/secrets.js"
import UserException from "../exceptions/UserException.js"
import UserRepository from "../repository/UserRepository.js"
import bcrypt from 'bcrypt'
import jwt from "jsonwebtoken"

class UserService {

  async getAccessToken(req) {
    try {
      const { email, password } = req.body;
      this.validateAccessRequest(email, password)
      const user = await UserRepository.findByEmail(email)
      this.valdiateResponse(user)
      await this.validateCredentials(password, user.password)
      const authUser = { id: user.id, name: user.name, email: user.email }
      const accessToken = jwt.sign({ authUser }, API_SECRET, { expiresIn: '1d' })

      return {
        status: httpStatus.SUCCESS,
        accessToken
      }
    } catch (err) {
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message
      }
    }

  }

  async findByEmail(req) {
    try {
      const { email } = req.params
      this.validateRequest(email)
      const user = await UserRepository.findByEmail(email)
      this.valdiateResponse(user)
      return {
        status: httpStatus.SUCCESS,
        user: {
          id: user.id,
          name: user.name,
          email: user.email
        }
      }
    } catch (err) {
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message
      }
    }

  }

  validateRequest(email) {
    if (!email) {
      throw new UserException(httpStatus.BAD_REQUEST, "User email was not informed!",)
    }
  }

  valdiateResponse(user) {
    if (!user) {
      throw new UserException(httpStatus.NOT_FOUND, "User not found!")
    }
  }

  validateAccessRequest(email, password) {
    if (!email || !password) {
      throw new UserException(httpStatus.BAD_REQUEST, "Email and password must be informed!")
    }
  }

  async validateCredentials(password, hashPass) {
    if (!await bcrypt.compare(password, hashPass)) {
      throw new UserException(httpStatus.UNAUTHORIZED, "Credentials doesn't match!")
    }
  }

}

export default new UserService();