// const friend1 = "Pera";
// const friend2 = "Mika";
// const friend3 = "Zika";

// // friend1 = "Izmenjen";

// const friends = [friend1, friend2, friend3];
// // const friendsNew = new Array(friend1, friend2);
// console.log(friend1);
// console.log(friends[0]);
// friends.pop();
// friends.push("NoviPrijatelj");
// friends.unshift("NoviNaPocetak");
// console.log(friends);

// // friends = [];

// const myInfo = ["Luka", 1998, friends];

// console.log(myInfo[0]);

// const me = {
//   firstName: "Luka",
//   birthYear: 1998,
//   friends: friends,
//   calcAge: function () {
//     console.log(this);
//     return 2022 - this.birthYear;
//   },
// };

// console.log(me.friends);
// console.log(me.calcAge(1998));
// console.log(me.calcAge());

// let age = 32;
// const newAge = age;
// age = 33;

// console.log("age:" + age);
// console.log("newAge:" + newAge);

// const you = Object.assign({}, me);
// you.firstName = "Perica";
// // you = {};
// you.friends.push("PericinNoviPrijatelj");

// // console.log(me);
// // console.log(you);

// const otherPerson = {};
// otherPerson.calcAge = me.calcAge;
// otherPerson.name = "OtherPerson";
// otherPerson.calcAge();
// // let a = 50;

// // if (60 > 50) {
// //   let a = 60;
// // }

// // console.log(a);

// console.log(this);
// console.log(friends);

// Array.prototype.vratiDrugi = function () {
//   return this[1];
// };

// console.log(friends.vratiDrugi());

const Person = function (firstName, birthYear) {
  this.firstName = firstName;
  this.birthYear = birthYear;
};

Person.prototype.calcAge = function () {
  return 2022 - this.birthYear;
};

const me = new Person("Luka", 1998);
console.log(me.calcAge());

console.log(me.__proto__.__proto__.__proto__);

class NewPerson {
  constructor(firstName, birthYear) {
    this.firstName = firstName;
    this.birthYear = birthYear;
  }
  calcAge() {
    console.log(2022 - this.birthYear);
  }

  get firstName() {
    return this._firstName;
  }

  set firstName(firstName) {
    this._firstName = firstName;
  }
}

const newMe = new NewPerson("Luka", 1998);
console.log(newMe);
console.log(newMe.firstName);

class Student extends NewPerson {
  constructor(firstName, birthYear, course) {
    super(firstName, birthYear);
    this.course = course;
  }

  calcAge() {
    console.log(2022 - this.birthYear + " i am student");
  }
}

const newStudent = new Student("Pera", 1998, "psw");
console.log(newStudent.calcAge());
