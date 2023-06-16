import { getPropertyByCodeReqBody, addPropertyUnitReqBody, delPropertyUnitReqBody } from "./requests.js";
import { getRandomInt } from './util.js'

const GQL_URL = 'http://localhost:8080/wh';

const PROPERTY_CODE = "HILTON_A";

const ACTIONS = ["+", "-"];

const STORED_UNIT_CODES = [];

const selectOp = () => {
    let i = getRandomInt(0,1)
    return ACTIONS[i]
}

const selectElementToRemove = () => {
    let randomIndex = getRandomInt(0, STORED_UNIT_CODES.length - 1)
    console.log(`REMOVING: ${STORED_UNIT_CODES[randomIndex]}`);
    return STORED_UNIT_CODES[randomIndex];
}

const remove = (element) => { 
    const index = STORED_UNIT_CODES.indexOf(element);
    const x = STORED_UNIT_CODES.splice(index, 1);
}

const addPropertyUnit = () => {
    fetch(GQL_URL, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: addPropertyUnitReqBody(PROPERTY_CODE),
        cache: 'no-store'
    })
    .then(res => res.json())
    .then(data => {
        console.log(JSON.stringify(data));
        console.log(data.data);
        console.log("===================================");
        if(data.data.addPropertyUnitToProperty.minimumPersons !== -1) {
            console.log("===================================");
            STORED_UNIT_CODES.push(data.data.addPropertyUnitToProperty.code)
            console.log("===================================");
        }
    })
}

const delPropertyUnit = (propertyUnitCode) => {
    fetch(GQL_URL, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: delPropertyUnitReqBody(PROPERTY_CODE, propertyUnitCode),
        cache: 'no-store'
    })
    .then(res => res.json())
    .then(data => {
        console.log(JSON.stringify(data));
        console.log(data.data);
        console.log(data.data?.deletePropertyUnitOfProperty);
        if(data.data?.deletePropertyUnitOfProperty === "SUCCESS"){
            remove(propertyUnitCode)
        }
        console.log("===================================");
    })
}

console.log(`STORE INIT: ${STORED_UNIT_CODES}`);
setInterval(() => {
    let s = selectOp()
    console.log(`OPERATION: ${s}`);
    switch (s) {
        case "+":
            addPropertyUnit();
            break;
        case "-":
            delPropertyUnit(selectElementToRemove());
            break;
    
        default:
            break;
    }
    console.log(STORED_UNIT_CODES);
}, 5000);