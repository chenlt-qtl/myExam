import * as services from './services';

const searchNames = term =>{
    const matches = services.getNames().filter(names=>names.includes(term))
    return matches.length>3? matches.slice(0,3):matches;
}

export default searchNames;