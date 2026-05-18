import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { TaskList } from './components/task-list/task-list';

@NgModule({
  declarations: [App, TaskList],
  imports: [BrowserModule, AppRoutingModule, FormsModule, BrowserModule],
  providers: [provideBrowserGlobalErrorListeners()],
  bootstrap: [App],
})
export class AppModule {}
