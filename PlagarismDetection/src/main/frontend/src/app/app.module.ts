import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { Routing } from './app.routing';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { TestService } from './services/test.service.client';
import { LoginComponent } from './components/user/login/login.component';

import { UserService } from './services/user.service.client';
import { SharedService } from "./services/shared.service";
import { CourseListComponent } from './components/course/course-list/course-list.component';
import { RegisterComponent } from './components/user/register/register.component';
import { CourseNewComponent } from './components/course/course-new/course-new.component';
import { AssignmentListComponent } from './components/assignment/assignment-list/assignment-list.component';
import { ResultListComponent } from './components/result/result-list/result-list.component';
import { CourseService } from "./services/course.service.client";
import { ResultViewComponent } from './components/result/result-view/result-view.component';
import { AssignmentService } from "./services/assignment.service.client";
import {ResultService} from "./services/result.service.client";
import {ProfileComponent} from "./components/user/profile/profile.component";
import { StatisticsComponent } from './components/statistics/statistics.component';
import { UserListComponent } from './components/user/user-list/user-list.component';
import {StatisticsService} from "./services/statistics.service.client";


@NgModule({
  // Declare components here
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    CourseListComponent,
    RegisterComponent,
    CourseNewComponent,
    AssignmentListComponent,
    ResultListComponent,
    ResultViewComponent,
    ProfileComponent,
    StatisticsComponent,
    UserListComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    Routing
  ],
  // Client Side services here
  providers: [TestService, UserService, SharedService, CourseService,
    AssignmentService, ResultService, StatisticsService],
  bootstrap: [AppComponent]
})

export class AppModule { }
