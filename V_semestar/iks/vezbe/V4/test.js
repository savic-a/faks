function test(str) {
    console.log(str);
}
test("test");
var p = 0;
p !== null && p !== void 0 ? p : (p = 10);
// var d = 10;
// if (20 > 10) {
//   var d = 40;
// }
var Person = /** @class */ (function () {
    function Person(firstName, lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    Person.prototype.calcAge = function () {
        console.log("Person test!");
    };
    return Person;
}());
// const personTest = new Person("Luka", "Doric");
// let pera: string = "Pera";
// const x: number = 5;
// const u: any = 5 - "2";
// let y: boolean = true;
// let z = 5;
// z = "str";
// console.log(pera);
var CarColor;
(function (CarColor) {
    CarColor[CarColor["Brown"] = 10] = "Brown";
    CarColor[CarColor["Blue"] = 15] = "Blue";
    CarColor[CarColor["Yellow"] = 20] = "Yellow";
})(CarColor || (CarColor = {}));
var toyota = {
    model: "",
    color: CarColor.Brown,
    calcAge: function (brGod) {
        throw new Error("Function not implemented.");
    },
};
var SuperCar = /** @class */ (function () {
    function SuperCar(model, color) {
        this.model = model;
        this.color = color;
    }
    return SuperCar;
}());
// function test(str: string): void {
//   console.log(str);
// }
