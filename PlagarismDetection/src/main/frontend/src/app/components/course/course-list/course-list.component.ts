import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {SharedService} from "../../../services/shared.service";
import {CourseService} from "../../../services/course.service.client";

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {

  user: any;
  userId: string;
  courses = [];
  errorFlag: boolean;
  errorMessage: string;
  infoFlag: boolean;
  infoMessage: string;


  constructor(private courseService: CourseService,
              private activatedRoute: ActivatedRoute,
              private sharedService: SharedService) {
  }

  ngOnInit() {
    this.user = this.sharedService.user;
    this.userId = this.sharedService.userId;
    this.errorFlag = false;
    this.infoFlag = false;


    this.courseService.findCourseForUser(this.userId).subscribe((courses) => {
      this.courses = courses;
      if (this.courses.length == 0) {
        this.infoMessage = "No courses available, please add a new course.";
        this.infoFlag = true;
      }
      console.log("COURSE LIST");
      console.log(this.courses[0]);
    }, (error) => {
      this.errorMessage = error._body;
      this.errorFlag = true;
    });
  }

}
