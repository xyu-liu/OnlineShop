export class User {
  constructor(
    public permission: string,
    private _token: string,
  ) {}

  get token() {
    return this._token;
  }
}
