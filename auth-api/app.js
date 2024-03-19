import express from "express";
import router from "./src/modules/user/routes/UserRouter.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

app.use(express.json());

app.get('/api/status', (req, res) => {
  return res.status(200).json({
    service: 'Auth-API',
    status: 'up',
    httpStatus: 200
  })
})

app.use(router)

app.listen(PORT, () => {
  console.info(`Server started at port: ${PORT}`)
})
