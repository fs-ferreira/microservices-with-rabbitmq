import axios from 'axios'
import { PRODUCT_API_URL } from '../../../config/constants/secrets.js'

class ProductClient {
  async checkProductStock(products, token) {
    try {
      const headers = {
        Authorization: token
      }
      console.info('Sending "check-stock" request to product-api...')
      const res = await axios
        .put(`${PRODUCT_API_URL}/checkStock`, {products}, { headers })
        .then(res => {
          return true
        })
        .catch(err => {
          console.error(err.response.data.message)
          return false
        })
        return res;
    } catch (err) {
      return false
    }
  }
}

export default new ProductClient();