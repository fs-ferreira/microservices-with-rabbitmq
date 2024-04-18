export const postRequestMessage = (primitive, data, transactionId, serviceId ) => {
  return `${primitive} - POST request with data ${data} | [transactionID: ${transactionId} | serviceID: ${serviceId}]`
}

export const postResponseMessage = (primitive, data, transactionId, serviceId ) => {
  return `${primitive} -  POST response with data ${data} | [transactionID: ${transactionId} | serviceID: ${serviceId}]`
}