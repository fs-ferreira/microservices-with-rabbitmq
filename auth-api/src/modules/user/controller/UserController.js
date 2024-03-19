import UserService from "../service/UserService.js";

class UserController {

  async getAccessToken(req, res){
    const response = await UserService.getAccessToken(req)
    return res.status(response.status).json(response)
  }

  async findByEmail(req, res) {
    const response = await UserService.findByEmail(req)
    return res.status(response.status).json(response)
  }
}

export default new UserController();