import bcrypt from 'bcrypt'
import User from '../../modules/user/model/User.js'

export default async function createInitialData() {
  await User.sync({ force: true })
  try {
    let password = await bcrypt.hash('123456', 10)
    await User.create({
      name: "admin",
      email: "admin@gmail.com",
      password
    })
    await User.create({
      name: "user",
      email: "user@gmail.com",
      password
    })
  } catch (err) {
    console.error(err.message)
  }
}