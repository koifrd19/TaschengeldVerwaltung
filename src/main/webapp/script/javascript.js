/**@Author Marcus Schweighofer (cooler smiler)*/

// wenn nicht angemeldet automatisch hacker!!
// 192.168.209.241
const BASE_URL = 'http://localhost:8080/taschengeldverwaltung/api/villager';

// FEHLT IM BACKEND!!!!

// --- login.html
// TODO: Alle login daten getten und durchgehen ob eine passt
function login() {
    let name = document.getElementById('name').value;
    let password = document.getElementById('password').value;
    if (name == 'max') {
        if (password == '1234') {
            window.location.replace(
                window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/')) +
                '/overview.html'
            );
        }
    }
};


// --- overview.html
function loadOverview() {
    fetch(BASE_URL + '/getAllVillager')
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
            return res.json();
        })
        .then(villagers => {
            displayOverview(villagers);
        })
        .catch(err => {
            console.log(err);
        });
};

function displayOverview(villagers) {
    let html = '<tr><th></th><th>Vorname</th><th>Nachname</th><th>Kürzel</th><th>Kontostand</th></tr>';
    for (const villager in villagers) {
        html += `<tr><td><a href='/taschengeldverwaltung/pages/villager_trunkdata.html?id=${villagers[villager].id}'>${villagers[villager].id}</a></td><td>${villagers[villager].firstName}</td><td>${villagers[villager].lastName}</td><td>${villagers[villager].shortSign}</td><td>${villagers[villager].balance}</td></tr>`;
    }
    document.getElementById('uebersichtTabelle').innerHTML = html;
};

function addVillager() {
    window.location.replace(
        window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/')) +
        '/villager_trunkdata.html?id=-1'
    );
};

// --- history.html
function loadHistory() {
    fetch(BASE_URL + '/getAllBookingHistory?sortedBy=date')
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
            console.log(res.json());
            return res.json();
        })
        .then(bookings => {
            displayHistory(bookings);
        })
        .catch(err => {
            console.log(err);
        });
};
// war schulkloschiss
function displayHistory(bookings) {
    let html = `<tr><th></th><th>Kürzel</th><th>BuchungsNR</th><th>Zweck</th><th>Anmerkung</th><th>Betrag</th><th>Kontostand</th></tr><tr><td><button onclick='fastBooking()'>Schnellbuchung:</button></td><td><input class='input' id='kuerzel'></td><td><input class='input' id='buchungsNR'></td><td><input class='input' id='zweck'></td><td><input class='input' id='anmerkung'></td><td><input class='input' id='betrag'></td><td></td></tr>`;
    for (booking in bookings) {
        html += `<tr><td></td><td>${bookings[booking].shortSign}</td><td>${bookings[booking].receiptNumber}</td><td>${bookings[booking].purpose}</td><td>${bookings[booking].note}</td><td>${bookings[booking].value}</td><td>KONTOSTAND</td></tr>`;
    }
    document.getElementById('buchTabelle').innerHTML = html;
}


function fastBooking() {
    //TODO: getting villagerID by kuerzel :(
    const shortSign = document.getElementById('kuerzel').value;
    const booking = {
        villagerId: villagerId2,
        dateTime: new Date(),
        username: 'max',
        value: document.getElementById('betrag'),
        receiptNumber: document.getElementById('buchungsNR'),
        note: document.getElementById('anmerkung'),
        purpose: {
            // TODO gleiches wie mit Salutation gar kein bock
        }
    }
    const init = {
        'Method': 'POST',
        booking
    }
    fetch(BASE_URL + '/postFastBooking', init)
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
        })
        .catch(err => {
            console.log(err);
        });
}

// --- villager_trunkdata.html
function changeLinkSoIsCorrect() {
    const villagerId = window.location.search.substring(window.location.search.indexOf("=") + 1);
    document.getElementById('v-history-leitung').setAttribute('href', './villager_history.html?id=' + villagerId);
    document.getElementById('v-stamm-leitung').setAttribute('href', './villager_trunkdata.html?id=' + villagerId);


}

function loadAllSalutations() {
    fetch(BASE_URL + '/getAllSalutations')
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
            return res.json();
        })
        .then(salutations => {
            displaySalutationsInDropdown(salutations);
        })
        .catch(err => {
            console.log(err);
        });
}

function loadAllRelations() {
    fetch(BASE_URL + '/getAllRelations')
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
            return res.json();
        })
        .then(relations => {
            displayRelationsInDropdown(relations);
        })
        .catch(err => {
            console.log(err);
        });
}

function loadAllTransitionMethods() {
    fetch(BASE_URL + '/getAllTransmissionMethods')
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
            return res.json();
        })
        .then(methods => {
            displayTransmissionMethodsInDropdown(methods);
        })
        .catch(err => {
            console.log(err);
        });
}

function loadTrunkDataVillager() {
    const villagerId = window.location.search.substring(window.location.search.indexOf("=") + 1);
    if (villagerId == -1) {
        return;
    }
    fetch(BASE_URL + '/getVillager?personId=' + villagerId)
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
            return res.json();
        })
        .then(villager => {
            displayTrunkDataVillager(villager);
        })
        .catch(err => {
            console.log(err);
        });
}

function loadTrunkDataTrusted() {
    const villagerId = window.location.search.substring(window.location.search.indexOf("=") + 1);
    if (villagerId == -1) {
        return;
    }
    fetch(BASE_URL + '/getTrustedPerson?personId=' + villagerId)
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
            return res.json();
        })
        .then(truster => {
            displayTrunkDataTruster(truster);
        })
        .catch(err => {
            console.log(err);
        });
}

function displaySalutationsInDropdown(salutations) {
    let html = '';
    let html2 = '';
    for (let salutation in salutations) {
        html += `<a onclick='updateTheCurrentSalutationB("${salutations[salutation].salutation}", ${salutations[salutation].id})'>${salutations[salutation].salutation}</a>`;
        html2 += `<a onclick='updateTheCurrentSalutationV("${salutations[salutation].salutation}", ${salutations[salutation].id})'>${salutations[salutation].salutation}</a>`;
    }
    document.getElementById('B-dropdown-anrede').innerHTML = html;
    document.getElementById('V-dropdown-anrede').innerHTML = html2;
}

function displayRelationsInDropdown(relations) {
    let html3 = '';
    for (let relation in relations) {
        html3 += `<a onclick='updateTheCurrentRelationV("${relations[relation].relation}", ${relations[relation].id})'>${relations[relation].relation}</a>`;
    }
    document.getElementById('V-dropdown-relation').innerHTML = html3;
}

function displayTransmissionMethodsInDropdown(methods) {
    let html4 = '';
    for (let method in methods) {
        html4 += `<a onclick='updateTheCurrentTransmissionMethodV("${methods[method].method}", ${methods[method].id})'>${methods[method].method}</a>`;
    }
    document.getElementById('V-dropdown-method').innerHTML = html4;
}

function updateTheCurrentSalutationB(newCurrent, id) {
    document.getElementById('B-anrede-button').setAttribute('data-content', newCurrent);
    document.getElementById('B-anrede-button').innerText = newCurrent;
    document.getElementById('B-anrede-button').setAttribute('name', id);
}

function updateTheCurrentSalutationV(newCurrent, id) {
    document.getElementById('V-anrede-button').setAttribute('data-content', newCurrent);
    document.getElementById('V-anrede-button').innerText = newCurrent;
    document.getElementById('V-anrede-button').setAttribute('name', id);
}

function updateTheCurrentRelationV(newCurrent, id) {
    document.getElementById('V-relation-button').innerText = newCurrent;
    document.getElementById('V-relation-button').setAttribute('name', id);
}

function updateTheCurrentTransmissionMethodV(newCurrent, id) {
    document.getElementById('V-method-button').innerText = newCurrent;
    document.getElementById('V-method-button').setAttribute('name', id);
}

function displayTrunkDataVillager(villager) {
    console.log(villager);

    document.getElementById("strong-name-b").innerText = villager.firstName + ' ' + villager.lastName;

    document.getElementById('div-alle-daten').setAttribute('name', villager.id);

    document.getElementById('B-firstname').value = villager.firstName;
    document.getElementById('B-lastname').value = villager.lastName;
    document.getElementById('B-shortsign').value = villager.shortSign;
    document.getElementById('B-titlepre').value = villager.titleBefore;
    document.getElementById('B-titlesuf').value = villager.titleAfter;

    updateTheCurrentSalutationB(villager.salutation.salutation, villager.salutation.id);

    document.getElementById('B-dateofbirth').value = villager.dateOfBirth;
    document.getElementById('B-dateofexit').value = villager.dateOfExit;
    document.getElementById('B-note').value = villager.note;
}

function displayTrunkDataTruster(trust) {
    console.log(trust);

    document.getElementById('daten-trust-person').setAttribute('name', trust.id);

    document.getElementById('V-firstname').value = trust.firstName;
    document.getElementById('V-lastname').value = trust.lastName;
    document.getElementById('V-titlepre').value = trust.titleBefore;
    document.getElementById('V-titlesuf').value = trust.titleAfter;

    updateTheCurrentSalutationV(trust.salutation.salutation, trust.salutation.id);
    updateTheCurrentRelationV(trust.relation.relation, trust.relation.id);
    updateTheCurrentTransmissionMethodV(trust.method.method, trust.method.id);

    document.getElementById('V-email').value = trust.email;
    document.getElementById('V-town').value = trust.town;
    document.getElementById('V-zipcode').value = trust.zipCode;
    document.getElementById('V-street').value = trust.street;
    document.getElementById('V-housenr').value = trust.houseNr;

}

function saveTrunkData() {
    const villagerPerson = JSON.stringify({
        firstName: document.getElementById('B-firstname').value,
        id: document.getElementById('div-alle-daten').getAttribute('name'),
        lastName: document.getElementById('B-lastname').value,
        salutation: {
            id: document.getElementById('B-anrede-button').getAttribute('name'),
            salutation: document.getElementById('B-anrede-button').innerText,
        },
        titleBefore: document.getElementById('B-titlepre').value,
        titleAfter: document.getElementById('B-titlesuf').value,
        dateOfBirth: document.getElementById('B-dateofbirth').value,
        dateOfExit: document.getElementById('B-dateofexit').value,
        note: document.getElementById('B-note').value,
        shortSign: document.getElementById('B-shortsign').value,
        trustedPerson: {
            id: document.getElementById('daten-trust-person').getAttribute('name')
        }
    });
    const trustedPerson = JSON.stringify({
        email: document.getElementById('V-email').value,
        firstName: document.getElementById('V-firstname').value,
        houseNr: document.getElementById('V-housenr').value,
        id: document.getElementById('daten-trust-person').getAttribute('name'),
        lastName: document.getElementById('V-lastname').value,
        method: {
            id: document.getElementById('V-method-button').getAttribute('name'),
            method: document.getElementById('V-method-button').innerText,
        },
        relation: {
            id: document.getElementById('V-relation-button').getAttribute('name'),
            relation: document.getElementById('V-relation-button').innerText,
        },
        salutation: {
            id: document.getElementById('V-anrede-button').getAttribute('name'),
            salutation: document.getElementById('V-anrede-button').innerText,
        },
        street: document.getElementById('V-street').value,
        telNr: document.getElementById('V-telephone').value,
        titleAfter: document.getElementById('V-titlesuf').value,
        titleBefore: document.getElementById('V-titlepre').value,
        town: document.getElementById('V-town').value,
        zipCode: document.getElementById('V-zipcode').value
    });

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    let requestOptions1 = {
        method: 'POST',
        headers: myHeaders,
        body: villagerPerson,
        redirect: 'follow'
    };

    let requestOptions2 = {
        method: 'POST',
        headers: myHeaders,
        body: trustedPerson,
        redirect: 'follow'
    };

    fetch(BASE_URL + '/postUpdateVillager', requestOptions1)
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
        })
        .catch(err => {
            console.log(err);
        });

    fetch(BASE_URL + '/postUpdateTrustedPerson', requestOptions2)
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
        })
        .catch(err => {
            console.log(err);
        });
}
// --- villager_history.html

function loadVillagerHistory() {
    const villagerId = window.location.search.substring(window.location.search.indexOf("=") + 1);
    fetch(BASE_URL + '/getVillagerBookingHistory?personId='+villagerId)
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
            console.log(res.json());
            return res.json();
        })
        .then(bookings => {
            displayVillagerHistory(bookings);
        })
        .catch(err => {
            console.log(err);
        });
}

function displayVillagerHistory(bookings) {
    let html = `<tr><th></th><th>BuchungsNR</th><th>Zweck</th><th>Anmerkung</th><th>Betrag</th><th>Kontostand</th></tr><tr><td><button onclick='fastBooking()'>Schnellbuchung:</button></td><td><input class='input' id='kuerzel'></td><td><input class='input' id='buchungsNR'></td><td><input class='input' id='zweck'></td><td><input class='input' id='anmerkung'></td><td><input class='input' id='betrag'></td><td></td></tr>`;
    for (booking in bookings) {
        html += `<tr><td></td><td>${bookings[booking].receiptNumber}</td><td>${bookings[booking].purpose}</td><td>${bookings[booking].note}</td><td>${bookings[booking].value}</td><td>KONTOSTAND</td></tr>`;
    }
    document.getElementById('buchTabelle').innerHTML = html;

}
// TODO: nicht zusammenlegen mit fastBooking()
function villagerFastBooking() {

}


























