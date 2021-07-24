# RevoluTap

A fork of Beats, a rhythm game with StepMania simfile compatibility.

Against the original developer's below advice, I've continued development of this game. While BeatX exists and likely has a much better input and scoring system, I've never been able to get the audio to sync up properly and the UI turns me off, so I continue to use this app to play StepMania simfiles. I originally forked this app to fix a crash that if you've used Beats before, you've likely uncovered. As the years have gone by, I've added new features and updated much of the ancient Android code. One big change is the removal of osu! support, as opsu! is a much better alternative and is also open source. Thank you so much Keripo for making the only mobile game I find worth playing, and I hope a others enjoy it as well.

New Features Include:
  - A song speed multiplier.
  - Song and song pack banners in the song selection menu.
  - The ability to set any number for scroll speed and song speed instead of selecting from a list.
  - SurfaceView with hardware acceleration backend.
  - Modern settings and song selection menus that follow system theme.
  - Android 11+ support including adding song packs using SAF.
  - No longer crashes when returning to the main menu.
  - And more!

A note to developers: This game's input system is super janky with multitouch. For example, when in a song, with one finger, press on a direction, then with another finger press on a direction and drag your finger to another direction. The position of the dragged press is never updated. If you can fix this and submit a PR, that would be awesome. I would merge it asap and credit you in this readme.

~Budsterblue


### Original Developer's Message:

This is the latest snapshot of the SVN code base (r18), migrated to GitHub.
This is the final release of Beats and the subsequent open sourcing of the code. The decision was made to not open source Beats until now due to the extremely messy and disorganized nature of the code (just about every feature since Beats 1.0a is an ugly hack). While Beats is now under a modified BSD license, please do NOT fork the original source code, for sanity reasons. The source code should be used for reference purposes only.
See http://beatsportable.com for more info

All source code is available under Modified BSD license.

~Keripo