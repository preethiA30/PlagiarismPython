import {Routes, RouterModule} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {LoginComponent} from './components/user/login/login.component';
import {RegisterComponent} from './components/user/register/register.component';
import {HomeComponent} from './components/home/home.component';
import {CourseListComponent} from "./components/course/course-list/course-list.component";
import {CourseNewComponent} from "./components/course/course-new/course-new.component";
import {AssignmentListComponent} from "./components/assignment/assignment-list/assignment-list.component";
import {ResultListComponent} from "./components/result/result-list/result-list.component";
import {ResultViewComponent} from "./components/result/result-view/result-view.component";
import {StatisticsComponent} from "./components/statistics/statistics.component";
import {UserListComponent} from "./components/user/user-list/user-list.component";


const APP_ROUTES: Routes = [
  { path : '', component : HomeComponent},
  { path : 'login' , component: LoginComponent},
  { path : 'register' , component: RegisterComponent },
  { path : 'admin/statistics' , component: StatisticsComponent},
  { path : 'admin/user' , component: UserListComponent},
  { path : 'user/course' , component: CourseListComponent},
  { path : 'user/course/new' , component: CourseNewComponent},
  { path : 'user/course/:cid/assignment' , component: AssignmentListComponent},
  { path : 'user/course/:cid/assignment/:aid/result' , component: ResultListComponent},
  { path : 'user/course/:cid/assignment/:aid/result/:rid' , component: ResultViewComponent},
];

export const Routing: ModuleWithProviders = RouterModule.forRoot(APP_ROUTES);
