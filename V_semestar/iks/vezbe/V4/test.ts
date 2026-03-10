function test(str: string) {
  console.log(str);
}

test("test");

let p: number = 0;
p ??= 10;

// var d = 10;

// if (20 > 10) {
//   var d = 40;
// }

class Person {
  constructor(public firstName: string, public lastName: string) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  calcAge(): void {
    console.log("Person test!");
  }
}

// const personTest = new Person("Luka", "Doric");

// let pera: string = "Pera";
// const x: number = 5;
// const u: any = 5 - "2";
// let y: boolean = true;
// let z = 5;
// z = "str";

// console.log(pera);

enum CarColor {
  Brown = 10,
  Blue = 15,
  Yellow = 20,
}

// console.log(CarColor.Blue);

interface Car {
  model: string;
  color?: CarColor;
  calcAge: (brGod: number) => any;
}

const toyota: Car = {
  model: "",
  color: CarColor.Brown,
  calcAge: function (brGod: number) {
    throw new Error("Function not implemented.");
  },
};

class SuperCar implements Car {
  constructor(public model: string, public color?: CarColor) {}
  calcAge: (brGod: number) => any;
}

// function test(str: string): void {
//   console.log(str);
// }
