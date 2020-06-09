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

package com.google.sps.data;

import java.util.ArrayList;

public class Comment {
    private String content;
    private Long timestamp;
    private Long id;
    private String date;
    private String user;
    private String tag;

    public Comment(String content, Long timestamp, Long id, String date, String user, String tag){
        this.content = content;
        this.timestamp = timestamp;
        this.id = id;
        this.date = date;
        this.user = user;
        this.tag = tag;
    }
}