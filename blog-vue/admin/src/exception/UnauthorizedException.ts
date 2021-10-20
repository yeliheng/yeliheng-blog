import { ApiException } from "./ApiException";

export class UnauthorizedExcetion extends ApiException{
    private code: number;
    constructor(message: string, errCode:string) {
        super(message,errCode);
        this.code = 401;
    }
}