#include "adjust_module.h"

#include "core/version.h"

#if VERSION_MAJOR == 4
#include "core/config/engine.h"
#else
#include "core/engine.h"
#endif

#include "adjust.h"

AdjustSdk *singleton = NULL;

void register_adjust_types() {
	singleton = memnew(AdjustSdk);
	Engine::get_singleton()->add_singleton(Engine::Singleton("Adjust", singleton));
}

void unregister_adjust_types() {
	if (singleton) {
		memdelete(singleton);
	}
}
