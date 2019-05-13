import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TagsChipsAutocompleteComponent } from './tags-chips-autocomplete.component';

describe('TagsChipsAutocompleteComponent', () => {
  let component: TagsChipsAutocompleteComponent;
  let fixture: ComponentFixture<TagsChipsAutocompleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TagsChipsAutocompleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TagsChipsAutocompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
