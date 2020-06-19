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

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
      Collection<String> people = request.getAttendees();
      Collection<TimeRange> availableTimes = new ArrayList<TimeRange>();
      availableTimes.add(TimeRange.WHOLE_DAY);
      for(Event event : events){
          if(!Collections.disjoint(event.getAttendees(), people)){
            createBookedRange(availableTimes, event.getWhen());
          }
      }

      availableTimes.removeIf(item -> (item.duration() < request.getDuration()));

      return availableTimes;
  }

  private void createBookedRange(Collection<TimeRange> availableTimes, TimeRange newEvent){
      for(TimeRange event : availableTimes){
          if(event.contains(newEvent)){
              availableTimes.add(TimeRange.fromStartEnd(event.start(), newEvent.start(), false));
              availableTimes.add(TimeRange.fromStartEnd(newEvent.end(), event.end(), false));
              availableTimes.remove(event);
              return;
        } else if(event.equals(newEvent)){
            availableTimes.remove(event);
            return;
        }  else if(newEvent.contains(event)){
            availableTimes.remove(event);
        } else if(event.overlaps(newEvent)){
            if(event.start() < newEvent.end()){
                availableTimes.add(TimeRange.fromStartEnd(newEvent.end(), event.end(), false));
                availableTimes.remove(event);
            } else if(newEvent.start() > event.end()){
                availableTimes.add(TimeRange.fromStartEnd(event.start(), newEvent.start(), false));
                availableTimes.remove(event);
            }
        }
      }
      return;
  }
}
