export const postRequestMessage = (primitive, data, transactionId, serviceId ) => {
  return `${primitive} - POST request with data ${data} | [transactionID: ${transactionId} | serviceID: ${serviceId}]`
}

export const postResponseMessage = (primitive, data, transactionId, serviceId ) => {
  return `${primitive} -  POST response with data ${data} | [transactionID: ${transactionId} | serviceID: ${serviceId}]`
}

export const getRequestMessage = (primitive, data, transactionId, serviceId ) => {
  return `${primitive} - GET request with data ${data} | [transactionID: ${transactionId} | serviceID: ${serviceId}]`
}

export const getResponseMessage = (primitive, data, transactionId, serviceId ) => {
  return `${primitive} -  GET response with data ${data} | [transactionID: ${transactionId} | serviceID: ${serviceId}]`
}

export const putRequestMessage = (primitive, data, transactionId, serviceId ) => {
  return `${primitive} - PUT request with data ${data} | [transactionID: ${transactionId} | serviceID: ${serviceId}]`
}

export const puttResponseMessage = (primitive, data, transactionId, serviceId ) => {
  return `${primitive} -  PUT response with data ${data} | [transactionID: ${transactionId} | serviceID: ${serviceId}]`
}