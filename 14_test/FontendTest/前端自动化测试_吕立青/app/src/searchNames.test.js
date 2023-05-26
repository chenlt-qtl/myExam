import searchNames from "./searchNames";

jest.mock("./services",()=>({
    getNames:jest.fn(()=>["John","Paul","George","Ringo"])
}))

test('should return empty result when not search',()=>{

    const keyword = 'Frank';
    const result = searchNames(keyword)
    expect(result).toEqual([])
})

