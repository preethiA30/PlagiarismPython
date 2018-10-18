import {Injectable} from '@angular/core';
import { Http, Response } from '@angular/http';
import {environment} from '../../environments/environment';

@Injectable()

export class CourseService {

  baseUrl = environment.baseUrl;

  constructor(private http: Http) { }

  api = {
    'createWebsite' : this.createCourse,
    'findCourseForUser' : this.findCourseForUser,
  };

  createCourse(userId: any, course: any) {
    const url = this.baseUrl + '/api/user/' + userId + '/course';
    return this.http.post(url, course)
      .map((response: Response) => {
        return response.json();
      });
  }

  findCourseForUser(userId: String) {
    const url = this.baseUrl + '/api/user/' + userId + '/course';
    console.log("HERE");
    return this.http.get(url).map((response: Response) => {
      // console.log(response);
      return response.json();
    });
  }

}

