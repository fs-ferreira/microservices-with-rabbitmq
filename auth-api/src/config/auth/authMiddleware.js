import jwt from 'jsonwebtoken'
import { promisify } from 'util'

import * as secrets from "../constants/secrets.js"
import * as httpStatus from "../constants/httpStatus.js"
import AuthException from './AuthException.js'

const bearer = "Bearer "

export default async (req, res, next) => {
  try {
    const { authorization } = req.headers;
    if (!authorization) {
      throw new AuthException(httpStatus.UNAUTHORIZED, "Access token must be provided!")
    }
    let token = authorization;
    if (token.toString().includes(bearer)) {
      token = token.replace(bearer, "")
    }
    const decoded = await promisify(jwt.verify)(token, secrets.API_SECRET)

    req.authUser = decoded.authUser
    return next();
  } catch (err) {
    const status = err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR;
    return res.status(status).json({
      status,
      message: err.message
    })
  }
}