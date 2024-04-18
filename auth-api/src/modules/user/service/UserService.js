import * as httpStatus from "../../../config/constants/httpStatus.js"
import { postRequestMessage, postResponseMessage } from "../../../config/constants/logMessages.js"
import { API_SECRET } from "../../../config/constants/secrets.js"
import UserException from "../exceptions/UserException.js"
import UserRepository from "../repository/UserRepository.js"
import bcrypt from 'bcrypt'
import jwt from "jsonwebtoken"

class UserService {

  async getAccessToken(req) {
    try {
      const { transactionid, serviceid } = req.headers;
      console.info(postRequestMessage('getAccessToken', JSON.stringify(req.body), transactionid, serviceid))

      const { email, password } = req.body;
      this.validateAccessRequest(email, password)
      const user = await UserRepository.findByEmail(email)
      this.valdiateResponse(user)
      await this.validateCredentials(password, user.password)
      const authUser = { id: user.id, name: user.name, email: user.email }
      const accessToken = jwt.sign({ authUser }, API_SECRET, { expiresIn: '1d' })

      const response = {
        status: httpStatus.SUCCESS,
        accessToken
      };

      console.info(postResponseMessage('getAccessToken', JSON.stringify(response), transactionid, serviceid))
      return response;
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
      const { authUser } = req;
      this.validateRequest(email)
      const user = await UserRepository.findByEmail(email)
      this.valdiateResponse(user)
      this.validateCurrentUser(user, authUser)
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

  validateCurrentUser(user, authUser) {
    if (!authUser || user.id !== authUser.id) {
      throw new UserException(httpStatus.FORBIDDEN, "Fobbiden request!")
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