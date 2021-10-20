export class ApiException extends Error {

    private errCode: string;

    constructor(message: string, errCode:string) {
        super(message);
        this.errCode = errCode;
    }
    
    public getErrCode(){
        return this.errCode;
    }


}