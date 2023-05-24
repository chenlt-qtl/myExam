
//toHaveBeenCalled
function drinkAll(callback, flavour) {
    if (flavour !== 'octopus') {
        callback(flavour);
    }
}


//toHaveBeenCalledTimes
function drinkEach(callback, flavours) {
    flavours.map(flavour => {
        callback(flavour);
    })
}
test('drinkEach drinks each drink', () => {
    const drink = jest.fn();
    drinkEach(drink, ['lemon', 'octopus']);
    expect(drink).toHaveBeenCalledTimes(2);
});

test('adding positive numbers is not zero', () => {
    for (let a = 1; a < 10; a++) {
        for (let b = 1; b < 10; b++) {
            expect(a + b).not.toBe(0);
        }
    }
});

test('null', () => {
    const n = null;
    expect(n).toBeNull();
    expect(n).toBeDefined();
    expect(n).not.toBeUndefined();
    expect(n).not.toBeTruthy();
    expect(n).toBeFalsy();
});

test('zero', () => {
    const z = 0;
    expect(z).not.toBeNull();
    expect(z).toBeDefined();
    expect(z).not.toBeUndefined();
    expect(z).not.toBeTruthy();
    expect(z).toBeFalsy();
});

test('two plus two', () => {
    const value = 2 + 2;
    expect(value).toBeGreaterThan(3);
    expect(value).toBeGreaterThanOrEqual(3.5);
    expect(value).toBeLessThan(5);
    expect(value).toBeLessThanOrEqual(4.5);

    // toBe and toEqual are equivalent for numbers
    expect(value).toBe(4);
    expect(value).toEqual(4);
});

test('float', () => {
    const value = 0.1 + 0.2;
    expect(value).not.toBe(0.3)
    expect(value).toBeCloseTo(0.3)
});

test('There is a "stop" in Christoph', () => {
    expect('Christoph').toMatch(/stop/);
});



test('shoppingList数组中包含milk', () => {
    const shoppingList = ['trash bags', 'paper towels', 'milk'];
    expect(shoppingList).toContain('milk');
    expect(new Set(shoppingList)).toContain('milk');
});

function compileAndroidCode() {
    throw new Error('you are using the wrong JDK!');
}

test('to throw exception', () => {
    expect(() => compileAndroidCode()).toThrow();
    expect(() => compileAndroidCode()).toThrow(Error);

    // You can also use a string that must be contained in the error message or a regexp
    expect(() => compileAndroidCode()).toThrow('you are using the wrong JDK');
    expect(() => compileAndroidCode()).toThrow(/JDK/);

    // Or you can match an exact error message using a regexp like below
    expect(() => compileAndroidCode()).toThrow(/^you are using the wrong JDK/);
    expect(() => compileAndroidCode()).toThrow(/^you are using the wrong JDK!$/);
});

describe('drinkAll', () => {
    test('drinks something lemon-flavoured', () => {
        const drink = jest.fn();
        drinkAll(drink, 'lemon');
        expect(drink).toHaveBeenCalled();
    });

    test('does not drink something octopus-flavoured', () => {
        const drink = jest.fn();
        drinkAll(drink, 'octopus');
        expect(drink).not.toHaveBeenCalled();
    });
});


describe('Promise', () => {
    const fetchData = () => new Promise((resolve, reject) => resolve("lemon"))
    test('resolves to lemon', () => {
        return expect(Promise.resolve('lemon')).resolves.toBe('lemon');
    });

    test('resolves to lemon', () => {
        return fetchData().then(data => {
            expect(data).toBe("lemon")
        });
    });

    test('resolves to lemon', async () => {
        const data = await fetchData()
        expect(data).toBe('lemon')
    });

    test('rejects to octopus', () => {
        return expect(Promise.reject(new Error('octopus'))).rejects.toThrow(
            'octopus',
        );
    });

    test('the fetch fails with an error', async () => {
        await expect(Promise.reject('octopus')).rejects.toMatch('octopus');
    });

    test('rejects to octopus', async () => {
        await expect(Promise.reject(new Error('octopus'))).rejects.toThrow("octopus")
    })


    test('the fetch fails with an error', () => {
        return Promise.reject('octopus').catch(e => expect(e).toMatch('octopus'));
    });
})