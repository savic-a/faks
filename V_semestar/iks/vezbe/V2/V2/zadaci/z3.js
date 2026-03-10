class Account {
    constructor(id, firstName, secondName, amount, actions) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.amount = amount;
        this.actions = actions;
    }
    addMoney(value) {
        this.amount = this.amount + value;
        console.log(this.amount);
    }
    subMoney(value) {
        this.amount = this.amount - value;
        console.log(this.amount);
    }
}

const account = new Account('123', 'Pera', 'Peric', 1200, [+300, -200, +100]);
account.addMoney(500);
account.addMoney(100);
account.subMoney(1000);