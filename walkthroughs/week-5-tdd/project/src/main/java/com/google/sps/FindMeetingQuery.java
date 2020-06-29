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
import com.google.sps.Pair;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
      ArrayList<String> requiredPeople = new ArrayList<String>(request.getAttendees());
      ArrayList<String> optionalPeople = new ArrayList<String>(request.getOptionalAttendees());
      Map<ArrayList<String>, SchedulingResult> history = new HashMap<>();

      Collection<TimeRange> answer = maximizeOptionalAttendees(requiredPeople, optionalPeople, optionalPeople.size()-1, events, request.getDuration(), history).schedule;
      return answer;
  }

  private ArrayList<TimeRange> getbookedTimes(Collection<Event> events, Collection<String> people) {
      ArrayList<TimeRange> bookedTimes = new ArrayList<>();
      for(Event event: events) {
          if(!Collections.disjoint(event.getAttendees(), people)){
            bookedTimes.add(event.getWhen());
          }
      }
      return bookedTimes;
  }

  private Collection<TimeRange> createAvailableRange(ArrayList<TimeRange> bookedTimes, long duration) {
      Collection<TimeRange> availableTimes = new ArrayList<>();
      Collections.sort(bookedTimes, TimeRange.ORDER_BY_START);
      int nextSlot = TimeRange.START_OF_DAY;
      for(TimeRange filled : bookedTimes) {
          if(filled.start() - nextSlot >= duration) {
              availableTimes.add(TimeRange.fromStartEnd(nextSlot, filled.start(), false));
          }

          if(filled.end() > nextSlot) {
            nextSlot = filled.end();
          }
      }

      if(TimeRange.END_OF_DAY - nextSlot >= duration) {
          availableTimes.add(TimeRange.fromStartEnd(nextSlot, TimeRange.END_OF_DAY, true));
      }
      return availableTimes;
  }

  private SchedulingResult numberOfAttendees(ArrayList<String> people, Collection<Event> events, long duration){
      ArrayList<TimeRange> bookedTime = getbookedTimes(events, people);
      Collection<TimeRange> availableRange = createAvailableRange(bookedTime, duration);
      SchedulingResult result = new SchedulingResult(people.size(), availableRange);
      return availableRange.isEmpty() ? new SchedulingResult(0, new ArrayList<TimeRange>()) : result;
  }

  private SchedulingResult maximizeOptionalAttendees(ArrayList<String> requiredPeople, ArrayList<String> optionalPeople, int n, Collection<Event> events, long duration, Map<ArrayList<String>, SchedulingResult> history){
    if(n<0){
        history.put(requiredPeople, numberOfAttendees(requiredPeople, events, duration));
        return history.get(requiredPeople);
    }

    if(history.containsKey(requiredPeople)){
        return history.get(requiredPeople);
    }
    else{
        ArrayList<String> temp2 = new ArrayList<String>(requiredPeople);
        temp2.add(optionalPeople.get(n));
        history.put(requiredPeople, maximizeOptionalAttendees(requiredPeople, optionalPeople, n-1, events, duration, history));
        history.put(temp2, maximizeOptionalAttendees(temp2, optionalPeople, n-1, events, duration, history));
        return comparison(history.get(requiredPeople), history.get(temp2)) ? history.get(requiredPeople) : history.get(temp2); 
    }
  }

  private class SchedulingResult{
      public Integer numOfPeople;
      public Collection<TimeRange> schedule;

      public SchedulingResult(int num, Collection<TimeRange> schedule){
          this.numOfPeople = num;
          this.schedule = schedule;
      }
  }

  public Boolean comparison(SchedulingResult left, SchedulingResult right){
      return left.numOfPeople > right.numOfPeople;
  }
}
