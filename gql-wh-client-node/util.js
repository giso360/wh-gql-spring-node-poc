import randomString from "random-string";

const STRING_UNIT_SEEDS = ["VILLA_", "APA_", "ROOM_"];              

// inclusive 0 - 10
export function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1) + min); // The maximum is inclusive and the minimum is inclusive
}

const getpostfix = () => {
    return randomString({ length: 8, numeric: false, letters: true, special: false }).substring(0, 3).toUpperCase();
}

const getunitType_ = () => { 
    return STRING_UNIT_SEEDS[Math.floor(Math.random() * STRING_UNIT_SEEDS.length)]
};

export function populatePropertyUnitInput() {
    let propertyUnitInput = {}
    let postfix = getpostfix()
    let unitType_ = getunitType_()

    let unitCode = unitType_ + postfix
    let unitType = unitType_.substring(0, unitType_.length - 1).toLowerCase()
    let name = unitCode.toLowerCase().replace("_", " ")
    let minimumPersons = getRandomInt(0, 10);
    let maximumPersons = getRandomInt(0, 10);
    
    propertyUnitInput.unitCode = unitCode;
    propertyUnitInput.unitType = unitType;
    propertyUnitInput.name = name;
    propertyUnitInput.minimumPersons = minimumPersons;
    propertyUnitInput.maximumPersons = maximumPersons;
    return propertyUnitInput
}

