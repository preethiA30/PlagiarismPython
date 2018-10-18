import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../../../services/user.service.client';
import {Router} from '@angular/router';
import 'rxjs/Rx';
import {SharedService} from '../../../services/shared.service';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @ViewChild('f') loginForm: NgForm;
  username: string;
  password: string;
  errorFlag: boolean;
  error = 'Invalid Username or Password!';


  constructor(private userService: UserService,
              private router: Router,
              private sharedService: SharedService) {
  }

  ngOnInit() {

  }

  login() {
    this.userService.login(this.username, this.password)
      .subscribe((data: any) => {
          console.log(data._body);

          this.sharedService.user = JSON.parse(data._body);

          this.sharedService.userId = this.sharedService.user._id['$oid'];

          this.sharedService.isAdmin = this.sharedService.user.isAdmin;

          this.errorFlag = false;
          if (this.sharedService.isAdmin) {
            this.router.navigate(['/admin/statistics'])
          } else {
            this.router.navigate(['/user/course'])
          }

        },
        (error: any) => {
          console.log(error);
          this.errorFlag = true;
        }
      );
  }
}
