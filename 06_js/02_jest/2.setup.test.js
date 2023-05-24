const setup = require('./2.setup')

beforeEach(() => {
    console.log("beforeEach...");
    setup.initializeCityDatabase();
});

beforeAll(() => {
    console.log("beforeAll...");
    return setup.initializeCityDatabase();
});

afterAll(() => {
    console.log("afterAll...");
    return setup.clearCityDatabase();
});

afterEach(() => {
    console.log("afterEach...");
    setup.clearCityDatabase();
});

test('city database has Vienna', () => {
    expect(setup.isCity('Vienna')).toBeTruthy();
});

test('city database has San Juan', () => {
    expect(setup.isCity('San Juan')).toBeTruthy();
});