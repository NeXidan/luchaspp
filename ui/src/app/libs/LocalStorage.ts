export function setItem(key: string, value: string) {
    try {
        localStorage.setItem(key, value);
    } catch (e) {
        // something wrong
    }
}

export function getItem(key: string) {
    var value;
    try {
        value = localStorage.getItem(key);
    } catch (e) {
        // something wrong
    }
    return value;
}

export function removeItem(key: string) {
    try {
        localStorage.removeItem(key);
    } catch (e) {
        // something wrong
    }
}
