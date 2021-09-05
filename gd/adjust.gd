extends Node

var _adjust = null

func _ready():
    if Engine.has_singleton("GodotAdjust"):
        _adjust = Engine.get_singleton("GodotAdjust")
    else:
        push_warning('Adjust plugin not found!')
    if ProjectSettings.has_setting('Adjust/AppToken'):
        var token = ProjectSettings.get_setting('Adjust/AppToken')
        init(token, not OS.is_debug_build())
    else:
        push_error('You should set Adjust/AppToken to SDK initialization')

func init(token: String, production := false) -> void:
    if _adjust != null:
        _adjust.init(token, production)
        print('Adjust plugin inited!')
        
func track_event(event: String) -> void:
    if _adjust != null:
        _adjust.trackEvent(event)

func track_revenue(event: String, revenue: float, currency := 'USD') -> void:
    if _adjust != null:
        _adjust.trackRevenue(event, revenue, currency)

