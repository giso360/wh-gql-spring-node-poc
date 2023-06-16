import { populatePropertyUnitInput } from "./util.js"

export const getPropertyByCodeReqBody = (pCode) => {
    return JSON.stringify({
        query: `
            query getPropertyByCode ($pCode: String!){
                propertyByCode(propertyCode: $pCode){
                code
                name
                type
                rating
                unitTypes{
                    id
                    code
                    name
                    unitType
                    minimumPersons
                    maximumPersons
                }
              }
            } 
        `,
        "variables":{"pCode": pCode}
    })
}

export const addPropertyUnitReqBody = (pCode) => {
  return JSON.stringify({
    query: `
      mutation addPropertyUnit ($propertyUnitInput: PropertyUnitTypesInput, $propertyCode: String!) {
        addPropertyUnitToProperty(propertyUnitInput: $propertyUnitInput, propertyCode: $propertyCode) {
          id
          code
          name
          unitType
          minimumPersons
          maximumPersons
        }
      }
    `,
    "variables":{
      "propertyUnitInput": populatePropertyUnitInput(),
      "propertyCode": pCode
    }
  })
}


export const delPropertyUnitReqBody = (pCode, propertyUnitCode) => {
  return JSON.stringify({
    query: `
      mutation deletePropertyUnitOfProperty($propertyUnitCode: String!, $propertyCode: String!){
        deletePropertyUnitOfProperty(propertyUnitCode: $propertyUnitCode, propertyCode: $propertyCode) 
      }
    `,
    "variables":{
      "propertyUnitCode": propertyUnitCode,
      "propertyCode": pCode
    }
  })
}

// ====================================================

// query - ΑDD
// ..... 

// mutation addPropertyUnit ($propertyUnitInput: PropertyUnitTypesInput, $propertyCode: String!) {
// 	addPropertyUnitToProperty(propertyUnitInput: $propertyUnitInput, propertyCode: $propertyCode) {
//     id
//     code
//     name
//     unitType
//     minimumPersons
//     maximumPersons
//   }
// }
    
// {
//   "propertyUnitInput": {
//     "unitCode": "VILLA_B",        
// 		"unitType": "villa",
// 		"name": "Black Room 2",
// 		"minimumPersons": 2,        
// 		"maximumPersons": 10  
//   },
//   "propertyCode": "HILTON_A"
// }
        
        
// ====================================================


// query - DEL
// ..... 

// mutation deletePropertyUnitOfProperty($propertyUnitCode: String!, $propertyCode: String!){
//   deletePropertyUnitOfProperty(propertyUnitCode: $propertyUnitCode, propertyCode: $propertyCode) 
// }

// {
//   "propertyUnitCode": "VILLA_A",
//   "propertyCode": "HILTON_A"
// }








