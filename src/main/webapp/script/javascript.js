/**@Author Marcus Schweighofer (cooler smiler)*/

// wenn nicht angemeldet automatisch hacker!!

const BASE_URL = 'http://localhost:8080/taschengeldverwaltung-1.0-SNAPSHOT/api/villager';

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
    let html = '<tr><th></th><th>Vorname</th><th>Nachname</th><th>Kürzel</th><th>Kontostand</th><th>Buchungsverlauf</th></tr>';
    for (let villager in villagers) {
        html += `<tr><td>${villager.id}</td><td>${villager.firstName}</td><td>${villager.lastName}</td><td>${villager.shortSign}</td><td>${villager.balance}</td><td><a href='./villager_history.html?id=${villager.id}'>Buchungsverlauf</a></td></tr>`;
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
    let html = `<tr><th></th><th>Kürzel</th><th>BuchungsNR</th><th>Zweck</th><th>Anmerkung</th><th>Betrag</th><th>Kontostand</th></tr><tr><td><button onclick='fastBooking()'>Schnellbuchung:</button></td><td><input class='input' id='kuerzel'></td><td><input class='input' id='buchungsNR'></td><td><input class='input' id='zweck'></td><td><input class='input' id='anmerkung'></td><td><input class='input' id='betrag'></td><td></td></tr>`;
    for (booking in bookings) {
        html += `<tr><td></td><td>${booking.shortSign}</td><td>${booking.receiptNumber}</td><td>${booking.purpose}</td><td>${booking.note}</td><td>${booking.value}</td><td>KONTOSTAND</td></tr>`;
    }
    document.getElementById('buchTabelle').innerHTML = html;
}

// TODO: fast booking dropdown für suche bzw dann gleich den primary key mitschicken ans frontend
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
// TODO: Villager id wird über query parameter übergeben (?)
function loadTrunkData() {
    fetch(BASE_URL + '/getVillager')
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
    document.getElementById('B-firstname').innerText = villager.firstName;
    document.getElementById('B-lastname').innerText = villager.lastName;
    document.getElementById('B-shortsign').innerText = villager.shortSign;
    document.getElementById('B-titlepre').innerText = villager.titleBefore;
    document.getElementById('B-titlesuf').innerText = villager.titleAfter;
    document.getElementById('B-salutation').innerText = villager.salutation;
    document.getElementById('B-dateofbirth').innerText = villager.dateOfBirth;
    document.getElementById('B-note').innerText = villager.note;
}

function saveTrunkData() {

}
// --- villager_history.html
function loadVillagerHistory() {

}
// TODO: vllt zusammenlegen mit fastBooking()
function villagerFastBooking() {

}
