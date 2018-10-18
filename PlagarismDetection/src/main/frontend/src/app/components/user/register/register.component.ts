import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from "../../../services/user.service.client";
import {Router} from "@angular/router";
import {SharedService} from "../../../services/shared.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  @ViewChild('f') registerForm: NgForm;
  registerWithGitCredentials: boolean;
  username: string;
  password: string;
  verifyPassword: string;
  gitHubUsername: string;
  gitHubPassword: string;
  verifyGitHubPassword: string;

  error: any;
  errorFlag: boolean;

  constructor(private userService: UserService,
              private router: Router,
              private sharedService: SharedService) {
  }

  ngOnInit() {
    this.registerWithGitCredentials = false;
  }

  toggleGitCredentials() {
    this.registerWithGitCredentials = !this.registerWithGitCredentials;
  }

  register() {
    if (this.registerWithGitCredentials) {
      this.registerWithGithub();
    } else {
      this.registerWithoutGithub();
    }
  }

  registerWithGithub() {
    if (this.username && this.password && this.gitHubUsername && this.gitHubPassword) {
      if (this.checkPasswordsMatch(this.password, this.verifyPassword)
        && this.checkPasswordsMatch(this.gitHubPassword, this.verifyGitHubPassword)
        && this.checkPasswordLength()) {

        const user = {
          username: this.username,
          password: this.password,
          gitHubUsername: this.gitHubUsername,
          gitHubPassword: this.gitHubPassword
        };

        this.userService.register(user).subscribe((data: any) => {
            this.login();
          },
          (error: any) => {
            this.error = error._body;
            this.errorFlag = true;
          }
        );
      }
    } else {
      console.log('fail');
      this.error = 'All Fields Required!';
      this.errorFlag = true;
    }
  }

  registerWithoutGithub() {
    if (this.username && this.password) {
      if (this.checkPasswordsMatch(this.password, this.verifyPassword)
        && this.checkPasswordLength()) {

        const user = {
          username: this.username,
          password: this.password,
          gitHubUsername: "",
          gitHubPassword: ""
        };

        this.userService.register(user)
          .subscribe(
            (data: any) => {
              this.login();
            },
            (error: any) => {
              this.error = error._body;
              this.errorFlag = true;
            }
          );

      }
    } else {
      this.error = 'All Fields Required!';
      this.errorFlag = true;
    }
  }

  checkPasswordLength() {
    if (this.password.length < 6) {
      this.error = 'Password must be at least 6 characters long!';
      this.errorFlag = true;
      return false;
    }

    return true;
  }

  checkPasswordsMatch(pass, verifyPass) {
    if (pass != verifyPass) {
      this.error = 'Passwords do not match!';
      this.errorFlag = true;
      return false;
    }

    return true;
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
