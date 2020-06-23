// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Iterator;

public final class FindMeetingQuery {
  public Collection<TimeRange> query (Collection<Event> events, MeetingRequest request) {
      Collection<String> people = request.getAttendees();
      Collection<TimeRange> availableTimes = new ArrayList<TimeRange>();

      ArrayList<TimeRange> bookedTimes = new ArrayList<TimeRange>();
      getbookedTimes(events, people, bookedTimes);
      Collections.sort(bookedTimes, TimeRange.ORDER_BY_START);
      createAvailableRange(bookedTimes, availableTimes, request.getDuration());

      
      return availableTimes;
  }

  private void getbookedTimes (Collection<Event> events, Collection<String> people, ArrayList<TimeRange> bookedTimes) {
      for(Event event: events){
          if(!Collections.disjoint(event.getAttendees(), people)){
            bookedTimes.add(event.getWhen());
          }
      }
  }

  private void createAvailableRange (ArrayList<TimeRange> bookedTimes, Collection<TimeRange> availableTimes, long duration) {
      int nextSlot = TimeRange.START_OF_DAY;
      for(TimeRange filled : bookedTimes){
          if(filled.start() - nextSlot >= duration){
              availableTimes.add(TimeRange.fromStartEnd(nextSlot, filled.start(), false));
          }

          if(filled.end() > nextSlot){
            nextSlot = filled.end();
          }
      }

      if(TimeRange.END_OF_DAY - nextSlot >= duration){
          availableTimes.add(TimeRange.fromStartEnd(nextSlot, TimeRange.END_OF_DAY, true));
      }
      return;
  }
}
