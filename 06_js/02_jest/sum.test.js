const sum = require('./sum')

//Don't use .toBe with floating-point numbers. Use .toBeCloseTo
test('add 1 + 2 to equal 3', () => {
    expect(sum(1, 2)).toBe(3);
})

test('add 2 + 2 not equal 3', () => {
    expect(sum(2, 2)).not.toBe(3);
})
