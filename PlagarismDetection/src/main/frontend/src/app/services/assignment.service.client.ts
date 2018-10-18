import {Injectable} from '@angular/core';
import { Http, Response } from '@angular/http';
import {environment} from '../../environments/environment';

@Injectable()

export class AssignmentService {

  baseUrl = environment.baseUrl;

  constructor(private http: Http) { }

  api = {
    'findAssignmentForCourse' : this.findAssignmentForCourse,
    'generateResults' : this.generateResults
  };

  findAssignmentForCourse(courseId: String) {
    const url = this.baseUrl + '/api/course/' + courseId + '/assignment';
    console.log("IN ASSIGNMENT CLIENT");
    return this.http.get(url).map((response: Response) => {
      return response.json();
    });
  }

  generateResults(userId: String, courseId: String, assignmentId: String, assignmentName: String, comparisonStrategy: String) {
    const url = this.baseUrl + '/api/user/' + userId + '/course/' + courseId + '/assignment/' + assignmentName + '/' + assignmentId + '/' + comparisonStrategy;
    return this.http.get(url).map((response: Response) => {
      return response.json();
    })
  }

}

