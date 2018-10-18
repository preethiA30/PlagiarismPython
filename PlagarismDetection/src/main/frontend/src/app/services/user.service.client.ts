import {Injectable} from '@angular/core';
import {Http, RequestOptions, Response} from '@angular/http';
import {environment} from '../../environments/environment';
import {SharedService} from './shared.service';
import {Router} from '@angular/router';


@Injectable()
export class UserService {

  baseUrl = environment.baseUrl;
  options = new RequestOptions();


  constructor(private http: Http, private sharedService: SharedService, private router: Router) { }

  api = {
    'createUser': this.createUser,
    'findUserById': this.findUserById,
    'findAllUsers' : this.findAllUsers,
    'findUserByUsername': this.findUserByUsername,
    'findUserByCredentials': this.findUserByCredentials,
    'deleteUser': this.deleteUser,
    'login': this.login,
    'register': this.register,
  };

  login(username: String, password: String) {

    this.options.withCredentials = true;

    const body = {
      username: username,
      password: password
    };

    console.log(body);

    return this.http.post( this.baseUrl + '/api/login', body).map((res: Response) => { return res });
  }

  register(user: any) {
    this.options.withCredentials = true;

    console.log(user);

    return this.http.post(this.baseUrl + '/api/register', user, this.options)
      .map(
        (res: Response) => {
          return res.json();
        }
      );
  }


  createUser(user: any) {
    const url = this.baseUrl + '/api/user';
    console.log('user.service.client::::', user);
    return this.http.post(url, user).map((response: Response) => {
      return response.json();
    });
  }

  findUserById(userId: string) {
    const url = this.baseUrl + '/api/user/' + userId;
    return this.http.get(url).map(
      (response: Response) => {
        return response.json();
      }
    );
  }

  findUserByUsername(username: string) {
    const url = this.baseUrl + '/api/user?username=' + username;
    return this.http.get(url).map((response: Response) => {
      return response.json();
    });
  }

  findUserByCredentials(username: string, password: string) {
    const url = this.baseUrl + '/api/user?username=' + username + '&password=' + password;
    return this.http.get(url)
      .map(
        (response: Response) => {
          return response.json();
        });
  }

  findAllUsers() {
    const url = this.baseUrl + '/api/admin/user';
    return this.http.get(url).map(
        (response: Response) => {
          return response.json();
        });
  }

  deleteUser(userId: string) {
    const url = this.baseUrl + '/api/admin/user/delete/' + userId;
    return this.http.delete(url, userId).map((response: Response) => {
      return null;
    });
  }

}
