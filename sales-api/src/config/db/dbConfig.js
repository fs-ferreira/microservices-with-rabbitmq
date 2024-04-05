import mongoose from "mongoose";
import { MONGO_DB_URL, MONGO_USERNAME, MONGO_PASSWORD } from "../constants/secrets.js"

export function connect() {
  mongoose.connect(MONGO_DB_URL, {
    authSource: MONGO_USERNAME,
    user: MONGO_USERNAME,
    pass: MONGO_PASSWORD
  })
  mongoose.connection.on('connected', function () {
    console.log("Connection with database has been stablished");
  })
  mongoose.connection.on('error', function () {
    console.error("Unable to connect to database");
  })
}