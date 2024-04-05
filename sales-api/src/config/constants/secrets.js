const env = process.env

export const MONGO_DB_URL = env.MONGO_DB_URL || "mongodb://localhost:27017/sales_db"
export const MONGO_USERNAME = env.MONGO_INITDB_ROOT_USERNAME || "admin"
export const MONGO_PASSWORD = env.MONGO_INITDB_ROOT_PASSWORD || "admin123"

export const API_SECRET = env.API_SECRET || "YXV0aC1hcGktc2VjcmV0LWRldmVsb3AtMTIzNA=="

export const RABBIT_MQ_URL = env.RABBIT_MQ_URL || "amqp://localhost:5672"