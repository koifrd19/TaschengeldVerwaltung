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

// TODO: Kontostand fehlt im Backend + Wo soll weiterleitung zu Stammdaten sein (echt firstname??)
function displayOverview(villagers) {
    let html = '<tr><th></th><th>Vorname</th><th>Nachname</th><th>K?rzel</th><th>Kontostand</th><th>Buchungsverlauf</th></tr>';
    for (const villager in villagers) {
        html += `<tr><td><a href='/taschengeldverwaltung/pages/villager_trunkdata.html?id=${villagers[villager].id}'>${villagers[villager].id}</a></td><td>${villagers[villager].firstName}</td><td>${villagers[villager].lastName}</td><td>${villagers[villager].shortSign}</td><td>${villagers[villager].balance}</td><td><a href='./villager_history.html?id=${villagers[villager].id}'>Buchungsverlauf</a></td></tr>`;
    }
    document.getElementById('uebersichtTabelle').innerHTML = html;
};

// --- history.html
function loadHistory() {
    fetch(BASE_URL + '/getAllBookingHistory')
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
            return res.json();
        })
        .then(bookings => {
            displayHistory(bookings);
        })
        .catch(err => {
            console.log(err);
        });
};

// TODO: Booking hat villagerId aber nicht kurzzeichen
function displayHistory(bookings) {
    // TODO: Zweck als Dropdown?
    let html = `<tr><th></th><th>K?rzel</th><th>BuchungsNR</th><th>Zweck</th><th>Anmerkung</th><th>Betrag</th><th>Kontostand</th></tr><tr><td><button onclick='fastBooking()'>Schnellbuchung:</button></td><td><input class='input' id='kuerzel'></td><td><input class='input' id='buchungsNR'></td><td><input class='input' id='zweck'></td><td><input class='input' id='anmerkung'></td><td><input class='input' id='betrag'></td><td></td></tr>`;
    for (booking in bookings) {
        html += `<tr><td></td><td>${booking.shortSign}</td><td>${booking.receiptNumber}</td><td>${booking.purpose}</td><td>${booking.note}</td><td>${booking.value}</td><td>KONTOSTAND</td></tr>`;
    }
    document.getElementById('buchTabelle').innerHTML = html;
}

// TODO: fast booking dropdown f?r suche bzw dann gleich den primary key mitschicken ans frontend
function fastBooking() {
    const shortSign = document.getElementById('kuerzel').value;
    const booking = {

    }
    const init = {
        'Method': 'POST'
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
// TODO: Villager id wird ?ber query parameter ?bergeben (?)
function loadTrunkData() {
    const villagerId = window.location.search.substring(window.location.search.indexOf("="));
    fetch(BASE_URL + '/getVillager?personId' + villagerId)
        .then(res => {
            if (!res.ok) {
                throw Error("HTTP-error: " + res.status);
            }
            return res.json();
        })
        .then(villager => {
            displayTrunkData(villager);
        })
        .catch(err => {
            console.log(err);
        });
}

function displayTrunkData(villager) {
    console.log(villager);

    document.getElementById('div-alle-daten').innerHTML = `class='columns is-centered' id='div-alle-daten' name='${villager.id}'`;

    document.getElementById('B-firstname').value = villager.firstName;
    document.getElementById('B-lastname').value = villager.lastName;
    document.getElementById('B-shortsign').value = villager.shortSign;
    document.getElementById('B-titlepre').value = villager.titleBefore;
    document.getElementById('B-titlesuf').value = villager.titleAfter;
    document.getElementById('B-salutation').value = villager.salutation.salutation;
    document.getElementById('B-dateofbirth').value = villager.dateOfBirth;
    document.getElementById('B-dateofexit').value = villager.dateOfExit;
    document.getElementById('B-note').value = villager.note;

    document.getElementById('V-firstname').value = villager.trustedPerson.firstName;
    document.getElementById('V-lastname').value = villager.trustedPerson.lastName;
    document.getElementById('V-shortsign').value = villager.trustedPerson.shortSign;
    document.getElementById('V-titlepre').value = villager.trustedPerson.titleBefore;
    document.getElementById('V-titlesuf').value = villager.trustedPerson.titleAfter;
    document.getElementById('V-salutation').value = villager.trustedPerson.salutation.salutation;

    document.getElementById('V-email').value = villager.trustedPerson.email;
    document.getElementById('V-town').value = villager.trustedPerson.town;
    document.getElementById('V-zipcode').value = villager.trustedPerson.zipCode;
    document.getElementById('V-street').value = villager.trustedPerson.street;
    document.getElementById('V-housenr').value = villager.trustedPerson.houseNr;
}

function saveTrunkData() {
    const villagerPerson = {

    }
}
// --- villager_history.html
function loadVillagerHistory() {

}
// TODO: vllt zusammenlegen mit fastBooking()
function villagerFastBooking() {

}
