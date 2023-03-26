# The App

The Trending  app displays trending GIFs in a grid layout from Giphy’s [/trending endpoint](https://developers.giphy.com/docs/api/endpoint#trending). Individual GIFs can be selected to enjoy them in all their glory.

# Screenshots
![](https://media.giphy.com/media/r37AVxgLxRvAW9fTRC/giphy-downsized-large.gif)

# The Task 
Although the code of the Trending app is not production code, we would like you to improve the app's quality. By quality, we mean both internal and external quality:
 - External: Noticeable to the user: Feature complete, no bugs, no memory leaks …
 - Internal: Noticeable to the engineers: Readable code, modern, no lint errors and Etc…

Please spend **no more than four hours** improving the quality of the app. We don’t expect you to implement all the acceptance criteria, please work on what you find most important (that may include things not on the list).

To make reviewing easier, please create a separate branch from the main. Add your commits to that branch. Submit a pull request when you complete the task.

# Acceptance Criteria 

1. In the overview, prefetching is not implemented, still, we rarely see empty images.
2. In the overview, GIFs are sometimes replaced with other GIFs.
3. Load more: We currently only load 25 images, it should load more GIFs if needed.
4. Prefetching: After fixing 1., we could implement prefetching so we rarely see “empty” GIFs.
5. Sharing option: On the detail screen the user should be able to share a GIF.
6. Overview: The layout on Landscape mode should switch to a three-column layout.
7. Detail screen: We should show the low-res GIF from the overview first, and replace it with the full version after loading.
8. The business logic should be covered with some Unit Tests.

# License

```
Copyright 2022 Mobimeo GmbH

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
