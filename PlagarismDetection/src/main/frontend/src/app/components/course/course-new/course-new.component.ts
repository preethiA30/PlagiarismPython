import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SharedService} from "../../../services/shared.service";
import {UserService} from "../../../services/user.service.client";
import {CourseService} from "../../../services/course.service.client";

@Component({
  selector: 'app-course-new',
  templateUrl: './course-new.component.html',
  styleUrls: ['./course-new.component.css']
})
export class CourseNewComponent implements OnInit {
  userId: any;
  name: String;
  repoUrl: String;
  courses = [];
  error: any;
  errorFlag: boolean;
  newCourseProgress: boolean;


  constructor(private courseService: CourseService,
              private activatedRoute: ActivatedRoute,
              private sharedService: SharedService,
              private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {
    this.userId = this.sharedService.userId;

  }


  createCourse() {
    if (this.name && this.repoUrl) {
      const course = {'name': this.name, 'repoUrl': this.repoUrl};
      this.newCourseProgress = true;

      this.courseService.createCourse(this.userId, course).subscribe((courses: any) => {
        this.courses = courses;
        console.log(courses);
        this.router.navigate(['/user', 'course']);
      }, error => {
        this.newCourseProgress = true;
        console.log(error);
        this.router.navigate(['/user', 'course']);
      });

    } else {
      this.error = 'All fields are required!';
      this.errorFlag = true;
    }
  }

}
