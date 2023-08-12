# Entity definitions


Each entity is described with a custom format:
- an entity began with { and ends wih }
- fields are separated by |
- first field is type of entity
- others fields depends of entity type
- fields may other entities, list of values, an action (random), ...


|   type    | shortcode | Note |
|-----------|-----------|------|
| itMonter  |           | Name + Attributes + list of entities* |
| itAgent   |           | |
| itHero    |           | |
| itValue   |        =  | Key Value |
| itList    |        ~  | Name + Entities|
| itCount   |        #  | for example Marks |
| itPercent |        %  |
| itRandom  |        @  |
| itArms    |           |
| itText    |           | Like itValue with key=text |
| itNote    |           |
| itToken   |           |

| List of attributes | |
|--------------------|-|
| Guts | |
| Wits | |
| Charm | |
| baseA | only for itMonter |
| baseD | only for itMonter |
| baseS | only for itMonter |



| Named list | used by | mandatory key | comment |
| --------- | ------ | -------- | ----- |
| opts | itMonster
| gear | itAgent, itMonster, itHero
| temp | itAgent, itMonster, itHero
| pack | itAgent, itMonster, itHero | Marks | |
| looks | itHero | Title
| store | itHero
| values |
| rank | itHero | Social |
| stat | itHero | Fame, Stipended | list of status affecting Hero |


*Example*
```
{
	itMonster|
	Panda|
	100|
	120|
	120|
	100|
	100|
	80|
	{=|pic|Shang/Panda.jpg}|
	{~|values|
		{=|passion|defensive}
	}
	{~|pack|
		{#|Gold Apple|2}|
		{@|Gold Apple|5}|
		{@|Enchant Scroll|5}|
		{@|Bless Scroll|2}|
		{%|Flame Scroll|80}|
		{%|Luck Scroll|60}|
		{%|Youth Elixir|20}|
		{%|Thief Insurance|60}|
		{%|Bottled Faery|8}
	}
	{~|temp|
		{#|Actions|3}|
		{#|magic|4}|
		{#|fight|2}
	}
	{~|gear|
		{%|Clawed Paws|0}|
		{%|Furry Hide|0}
		}
	{~|opts|help|backstab|swindle|control}|
	{itText|text|A blind old woman is walking with the aid of her trained panda.  She starts gabbling at you in that strange tongue they use here.  She seems to need directions, good luck with pointing.}
}
```
