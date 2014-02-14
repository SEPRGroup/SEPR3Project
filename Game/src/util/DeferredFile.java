package util;

import org.newdawn.slick.loading.DeferredResource;

public abstract class DeferredFile implements DeferredResource {
	protected final String filename;
	
	public DeferredFile(String filename) {
		this.filename = filename;
	}

	@Override
	public final String getDescription() {
		return filename;
	}

}
