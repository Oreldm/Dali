import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {FormControl} from '@angular/forms';
import {MatAutocompleteSelectedEvent, MatChipInputEvent, MatAutocomplete} from '@angular/material';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { RestService } from '../_services/rest.service';
import { Tag } from '../_models/tag';

@Component({
  selector: 'app-tags-chips-autocomplete',
  templateUrl: './tags-chips-autocomplete.component.html',
  styleUrls: ['./tags-chips-autocomplete.component.styl']
})
export class TagsChipsAutocompleteComponent implements OnInit {
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  tagCtrl = new FormControl();
  filteredTags: Observable<string[]>;
  tags: string[] = [];
  allTags: string[] = [/*'Architectural', 'Historically', 'Abstract', 'Digital', 'Steam-Punk', 'Fantasy', 'Food'*/];
  tagIdMap: Map<string, number> = new Map();

  @ViewChild('tagInput') tagInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor(private rest: RestService) {
    this.filteredTags = this.tagCtrl.valueChanges.pipe(
        startWith(null),
        map((tag: string | null) => tag ? this._filter(tag) : this.allTags.slice()));
  }

  ngOnInit() {
    this.rest.getTags().subscribe(data => {
      console.log(data);
      this.setTagsList(JSON.parse(JSON.stringify(data)))
    })
  }

  add(event: MatChipInputEvent): void {
    // Add tag only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = event.value;

      // Add our tag
      if ((value || '').trim()) {
        this.tags.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.tagCtrl.setValue(null);
    }
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const value = event.option.viewValue;
    this.tags.push(value);
    this.tagInput.nativeElement.value = '';
    this.tagCtrl.setValue(null);
  }

  public getSelectedTagId() : string {
    return this.tagIdMap.get(this.tags[0].toLowerCase()) + "";
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allTags.filter(tag => tag.toLowerCase().indexOf(filterValue) === 0);
  }
  
  public setTagsList(resList: any[]) {
    resList.forEach(tag => {
      this.allTags.push(this.capitalizeString(tag.name))
      this.tagIdMap.set(tag.name, tag.id)
    })
  }

  private capitalizeString(str: string): string{
    if(typeof str !== 'string')
      return '';
    return str.charAt(0).toUpperCase() + str.slice(1)
  }
}
