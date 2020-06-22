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
      Collection<Event> eventsAL = new ArrayList<Event>(events);
      Collection<String> people = request.getAttendees();

      Collection<TimeRange> availableTimes = new ArrayList<TimeRange>();
      availableTimes.add(TimeRange.WHOLE_DAY);
      getAvailableTimes(eventsAL, people, availableTimes, request.getDuration());
    
      Collection<TimeRange> availableTimesOptional = new ArrayList<TimeRange>(availableTimes);
      Collection<String> optionalPeople = request.getOptionalAttendees();
      getAvailableTimes(eventsAL, optionalPeople, availableTimesOptional, request.getDuration());

      if(request.getAttendees().isEmpty() && availableTimesOptional.isEmpty()){
          return Arrays.asList();
      }

      return availableTimesOptional.isEmpty() ? availableTimes : availableTimesOptional;
  }

  private void getAvailableTimes (Collection<Event> events, Collection<String> people, Collection<TimeRange> availableTimes, long duration) {
      Iterator<Event> it = events.iterator();
      while(it.hasNext()){
          Event element = it.next();
          if(!Collections.disjoint(element.getAttendees(), people)){
            createAvailableRange(availableTimes, element.getWhen());
            it.remove();
          }
      }
      availableTimes.removeIf(item -> (item.duration() < duration));
      return;
  }

  private void createAvailableRange (Collection<TimeRange> availableTimes, TimeRange newEvent) {
      for(TimeRange event : new ArrayList<TimeRange>(availableTimes)){
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
            } else if(newEvent.start() < event.end()){
                availableTimes.add(TimeRange.fromStartEnd(event.start(), newEvent.start(), false));
                availableTimes.remove(event);
            }
        }
      }
      return;
  }
}
