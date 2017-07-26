# MorphAnimation

Easy animation of view type change

## Sample

![alt text](https://github.com/protalor/MorphAnimation/blob/master/static/MorphAnimation_400.gif?raw=true)

## Use

When the view changes, call the static method

```
    findViewById(R.id.width_wrap_to_match).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!v.isSelected()) {
                v.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                v.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            v.setSelected(!v.isSelected());
            v.requestLayout();

            MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE_WIDTH);
        }
    });


    findViewById(R.id.height_visible_to_gone).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            v.setVisibility(View.GONE);
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    v.setVisibility(View.VISIBLE);
                    MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE_HEIGHT);
                }
            }, 1000);
            MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE_HEIGHT);
        }
    });


    findViewById(R.id.height_x2).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!v.isSelected()) {
                v.getLayoutParams().height = (int) convertDpToPixel(140, MainActivity.this);
            } else {
                v.getLayoutParams().height = (int) convertDpToPixel(70, MainActivity.this);
            }
            v.setSelected(!v.isSelected());
            v.requestLayout();

            MorphAnimation.morph(v);
        }
    });


    findViewById(R.id.width_height_center).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            v.setVisibility(View.GONE);
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    v.setVisibility(View.VISIBLE);
                    MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
                }
            }, 1000);
            MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
        }
    });

    findViewById(R.id.width_height_left).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            v.setVisibility(View.GONE);
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    v.setVisibility(View.VISIBLE);
                    MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
                }
            }, 1000);
            MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
        }
    });

    findViewById(R.id.width_height_right).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            v.setVisibility(View.GONE);
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    v.setVisibility(View.VISIBLE);
                    MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
                }
            }, 1000);
            MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
        }
    });
```

## License

Copyright 2017 protales

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
