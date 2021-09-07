import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, throwError } from 'rxjs';
import { Gateway } from '../models/gateway';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class GatewayService {


  gateway!: Gateway;

  gatewayUrl = 'http://localhost:8081/gateway';

  
  isEdit!: boolean;

  constructor(private http: HttpClient) { }

    // Observable string sources
    private emitChangeSource = new Subject<any>();
    // Observable string streams
    changeEmitted$ = this.emitChangeSource.asObservable();
    // Service message commands
    emitChange(change: any) {
      this.emitChangeSource.next(change);
    }

  getGateways(): Observable<Gateway[]> {
    return this.http.get<Gateway[]>(this.gatewayUrl + '/getAllGateways');
  }

  getGateway(id: number): Observable<Gateway> {
    const url = `${this.gatewayUrl}/getGateway/${id}`;
    console.log('>>>>>>>>>>>>>>>> ' + url);
    return this.http.get<Gateway>(url);
  }

  saveGateway(gateway: Gateway): Observable<Gateway> {
    return this.http.post<Gateway>(this.gatewayUrl+'/addGateway', gateway, httpOptions);
  }

  updateGateway(gateway: Gateway): Observable<Gateway> {
    const url = `${this.gatewayUrl}/updateGateway/${gateway.id}`;
    return this.http.put<Gateway>(url, gateway, httpOptions);
  }

  removeGateway(gateway: Gateway | number): Observable<Gateway> {
    const id = typeof gateway === 'number' ? gateway : gateway.id;
    const url = `${this.gatewayUrl}/deleteGateway/${id}`;
    return this.http.delete<Gateway>(url, httpOptions);
  }


  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error.errorMessage}`);
    }
    // return an ErrorObservable with a user-facing error message
    // return new ErrorObservable replaced with throwError
    return throwError('Something bad happened; ' + error.error.errorMessage);
  }

}
