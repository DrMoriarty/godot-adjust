#pragma once

#include "core/version.h"

#if VERSION_MAJOR == 4
#include "core/object/class_db.h"
#else
#include "core/object.h"
#endif

class AdjustSdk : public Object {

	GDCLASS(AdjustSdk, Object);

	static AdjustSdk *instance;
	static void _bind_methods();

public:

	static AdjustSdk *get_singleton();

    void init(String token, bool production);
    void trackEvent(String event);
    void trackRevenue(String event, double revenue, String currency);

	AdjustSdk();
	~AdjustSdk();
};

