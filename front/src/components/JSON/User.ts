export class User {
  userId: number;
  firstname: string;
  lastname: string;
  login: string;
  email: string;
  password: string;
  cash: number;

  constructor(
    userId: number,
    firstname: string,
    lastname: string,
    login: string,
    email: string,
    password: string,
    cash: number
  ) {
    this.userId = userId;
    this.firstname = firstname;
    this.lastname = lastname;
    this.login = login;
    this.email = email;
    this.password = password;
    this.cash = cash;
  }

  static fromJson(json: any): User {
    return new User(
      json.userId,
      json.firstname,
      json.lastname,
      json.login,
      json.email,
      json.password,
      json.cash
    );
  }
}
